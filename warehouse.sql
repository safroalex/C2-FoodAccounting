
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- Таблица "Единицы измерения" (Unit)
CREATE TABLE Unit (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    Name VARCHAR(100),
    Description TEXT
);

-- Справочник "Группы продуктов" (ProduktGroup)
CREATE TABLE ProduktGroup (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    Name VARCHAR(100)
);

-- Таблица "Продукты" (Product)
CREATE TABLE Product (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    ProduktGroupId UUID REFERENCES ProduktGroup(ID),
    Name VARCHAR(100)
);



-- Таблица "Склад" (Warehouse)
CREATE TABLE Warehouse (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    DateDeposit DATE,
    Remark TEXT
);

-- Таблица "Количество продовольствия" (QuantityProdukt)
CREATE TABLE QuantityProdukt (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    WarehouseId UUID REFERENCES Warehouse(ID),
    ProduktId UUID REFERENCES Product(ID),
    Number INT,
    UnitId UUID REFERENCES Unit(ID), 
    DateExpiry DATE,
    Remark TEXT
);





INSERT INTO ProduktGroup (Name) VALUES
('Молочные продукты'),
('Хлебобулочные изделия'),
('Мясные продукты');

INSERT INTO Unit (Name, Description) VALUES
('кг', 'Килограммы'),
('л', 'Литры'),
('шт', 'Штуки');

-- Предполагается, что ID групп уже известны или можно использовать SELECT для получения их
-- Например, предположим, что ID для "Молочные продукты" это 1, для "Хлебобулочные изделия" это 2 и т.д.


INSERT INTO Product (ProduktGroupId, Name) VALUES
((SELECT ID FROM ProduktGroup WHERE Name = 'Молочные продукты'), 'Молоко'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Молочные продукты'), 'Кефир'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Хлебобулочные изделия'), 'Хлеб'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Хлебобулочные изделия'), 'Булочка'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Мясные продукты'), 'Курица'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Мясные продукты'), 'Говядина');
