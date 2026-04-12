-- Простой KEYSPACE с одной репликой.

drop KEYSPACE if exists mobishop;

CREATE KEYSPACE IF NOT EXISTS mobishop
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 1
        };
