-- KEYSPACE для инсталляций из 3х узлов: 3 цода, по 1 в каждом цоде

drop KEYSPACE if exists mobishop;


CREATE KEYSPACE mobishop
    WITH REPLICATION = {
        'class' : 'NetworkTopologyStrategy',
        'LORDAERON' : 1, 'KALIMDOR': 1, 'NORTHREND' : 1
        };
