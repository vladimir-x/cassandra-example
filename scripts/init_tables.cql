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


-- Таблица с резервом
drop table IF EXISTS mobishop.reserve;
CREATE TABLE IF NOT EXISTS mobishop.reserve
(
    serialNumber text,
    barcode      text,
    fio          text,
    phone        text,

    PRIMARY KEY ((serialNumber))
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
