-- Таблица с содержимым склада
drop table IF EXISTS mobishop.catalog;
CREATE TABLE IF NOT EXISTS mobishop.catalog
(
    barcode text,
    brand   text,
    name    text,
    cost    bigint,
    PRIMARY KEY (barcode)
);

-- Таблица с ведением остатков
drop table IF EXISTS mobishop.balance;
CREATE TABLE IF NOT EXISTS mobishop.balance
(
    barcode text,
    amount  bigint,
    version bigint,
    PRIMARY KEY (barcode)
);


-- Таблица с продажами
drop table IF EXISTS mobishop.receipt;
CREATE TABLE IF NOT EXISTS mobishop.receipt
(
    shop_id     bigint,
    day         date,
    sale_date   timestamp,
    receipt_id  text,

    totalAmount bigint,
    seller      text,
    barcodes    frozen<list<text>>,
    PRIMARY KEY ((shop_id, day), sale_date, receipt_id)
);
