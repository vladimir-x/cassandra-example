-- KEYSPACE для инсталляций из 6-ти узлов: 2 цода, по 3 в каждом цоде

drop KEYSPACE if exists mobishop;


CREATE KEYSPACE mobishop
    WITH REPLICATION = {
        'class' : 'NetworkTopologyStrategy',
        'Left' : 3, 'Right': 3
        };
