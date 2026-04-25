-- KEYSPACE для инсталляций из 6-ти узлов: 2 цода, по 3 в каждом цоде

drop KEYSPACE if exists mobishop;


CREATE KEYSPACE mobishop
    WITH REPLICATION = {
        'class' : 'NetworkTopologyStrategy',
        'LORDAERON' : 3, 'KALIMDOR': 3
        };
