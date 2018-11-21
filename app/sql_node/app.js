var express = require('express');
var app = express();
var mysql = require('mysql');
var bodyParser = require('body-parser')

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var pool      =    mysql.createPool({
    connectionLimit : 5, 
    host     : 'localhost',
    user     : 'root',
    password : 'senha1',
    database : 'agenda',
    debug    :  false
});


function fullUrl(req) {
	var url = require('url');
	return url.format({
		protocol: req.protocol,
		hostname: req.hostname,
		pathname: req.originalUrl
	});
}


function getJson(req){
	return new Buffer(req.params.objeto, 'base64').toString('ascii');
}


function my(query, res){
	
	pool.getConnection(function(err,connection){
		if (err) {
			connection.release();
			res.status(400).json({'status' : 'Failed', "msg" : "Error in connection database"});
			return;
		}   

		connection.query(query, function(err, rows){
			connection.release();
			if(!err) {

				var comando = query.trim().toLowerCase().substring(0, 6);
				if(comando == 'select' ){
					res.status(200).json( rows );

				} else if(comando == 'insert' && rows.affectedRows > 0) {
					res.status(200).json({ "status": "Sucesso", 'id': rows.insertId, "rows" : rows.affectedRows });

				} else if(comando == 'update' && rows.changedRows > 0) {
					res.status(200).json({ "status": "Sucesso", "rows": rows.changedRows });

				} else if((comando == 'replac' || comando == 'delete' || comando.substring(0, 4) == 'call') && 
						rows.affectedRows > 0 ) {
					res.status(200).json({ "status": "Sucesso", "rows": rows.affectedRows });

				} else {
					res.status(400).json({'status': 'Failed', 'msg': 'Nenhum registro afetado/encontrado' });
				}
			} else {
				console.log('query error: ' + query );
				res.status(400).json({'status' : 'Failed', "msg" : "Error in query"});
			}
			return;     
		});

		connection.on('error', function(err) {      
			res.status(400).json({'status' : 'Failed', "msg" : "Error in connection database"});
			return;     
		});
	});

}


app.get('/query/:query', function (req, res) {
	var rows = my( req.params.query, res );
	my( query, res );
})

app.get('/usuario', function (req, res) {
	var query = 'select u.*, t.valor as tipo_descricao '+
		'from usuario u ' + 
		'join tipo t on t.tabela=\'usuario\' and t.campo=\'tipo\' and t.chave = u.tipo ';
	my( query, res );
})

app.get('/especialidade', function (req, res) {
	var query = 'select * from especialidade order by nome ';
	my( query, res );
})


app.get('/disponibilidade/:especialidade', function (req, res) {
	var query = 'SELECT * ' +
		'FROM disponibilidade ' +
		'where especialidade = ' + req.params.especialidade + 
		' order by data ';
	my( query, res );
})


app.get('/usuarioTipo/:tipo', function (req, res) {
	var query = 'select * from usuario  ' + 
		'where tipo like \'' + req.params.tipo + '\' order by nome ';
	my( query, res );
})


app.get('/usuario/:usuario', function (req, res) {
	var query = 'select u.*, t.valor as tipo_descricao '+
		'from usuario u ' + 
		'join tipo t on t.tabela=\'usuario\' and t.campo=\'tipo\' and t.chave = u.tipo ' +
		'where usuario like \'' + req.params.usuario + '\'';
	my( query, res );
})


app.put('/usuario/:id', function (req, res) {
	var u = req.body;
	var query = 'update usuario set token = \'' + u.token + '\' where id = ' + req.params.id ;
	my( query, res );
})


app.get('/consulta/:tipo/:usuario', function (req, res) {
	var query  = 'select c.* , d.data, d.periodo, ' +
		'p.nome as paciente, m.nome as medico, e.nome as especialidade ' +
		'from consulta c ' +
		'join disponibilidade d on d.id = c.disponibilidade ' +
		'join usuario p on p.id = c.usuario ' +
		'join usuario m on m.id = d.usuario ' +
		'join especialidade e on e.id = d.especialidade ';
	if(req.params.tipo == 'P'){
		query += 'where c.usuario like \'' + req.params.usuario + '\' order by d.data, c.hora, c.minuto';
	} else if(req.params.tipo == 'M'){
		query += 'where d.usuario like \'' + req.params.usuario + '\' order by d.data, c.hora, c.minuto';
	} else {
		query += 'order by d.data, c.hora, c.minuto';
	}
	my( query, res );
})

app.get('/consulta/:disponibilidade', function (req, res) {
	var query  = 'select * from consulta ' +
		'where disponibilidade = ' + req.params.disponibilidade + 
		' order by hora, minuto';
	my( query, res );
})


app.put('/consulta/:id', function (req, res) {
	var c = req.body;
	var query = 'update consulta set ' +
		'disponibilidade = ' + c.disponibilidade + ', ' +
		'hora = ' + c.hora + ', ' +
		'minuto = ' + c.minuto + ', ' +
		'usuario = ' + c.usuario + ', ' +
		'confirmado = ' + c.confirmado + ' ' +
		'where id = ' + req.params.id ;
	my( query, res );
})


app.post('/consulta', function (req, res) {
	var c = req.body;
	var query = 'INSERT INTO consulta ' + 
			'( disponibilidade, hora, minuto, usuario ) ' +
		'VALUES ( ' + c.disponibilidade + ', ' + c.hora + ', ' + 
			c.minuto + ', ' + c.usuario + ' )';
	my( query, res );
})


app.delete('/consulta/:id', function (req, res) {
	var query = 'delete from consulta where id = ' + req.params.id ;
	my( query, res );
})


app.listen(8080, function () {
  console.log('Server running on port 8080!')
})

