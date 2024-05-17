
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




-- Добавление групп продуктов
INSERT INTO ProduktGroup (Name) VALUES
('Крупы и макаронные изделия'),
('Молочные продукты'),
('Овощи'),
('Мясные изделия'),
('Приправы и добавки'),
('Кондитерские изделия'),
('Напитки'),
('Специфические ингредиенты');

-- Добавление единиц измерения
INSERT INTO Unit (Name, Description) VALUES
('грамм', 'Граммы'),
('миллилитры', 'Миллилитры');


-- Добавление продуктов
INSERT INTO Product (ProduktGroupId, Name) VALUES
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупа'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Макаронные изделии'),
-- Молочные продукты
((SELECT ID FROM ProduktGroup WHERE Name = 'Молочные продукты'), 'Молоко'),
-- Овощи
((SELECT ID FROM ProduktGroup WHERE Name = 'Овощи'), 'Картофель'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Овощи'), 'Морковь'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Овощи'), 'Свекла'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Овощи'), 'Лук репчатый'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Овощи'), 'Капуста'),
-- Мясные изделия
((SELECT ID FROM ProduktGroup WHERE Name = 'Мясные изделия'), 'Мясо сырое'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Мясные изделия'), 'Сосиски'),
-- Приправы и добавки
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Соль'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Томатная паста'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Кулинарные жиры'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Масло растительное'),
-- Кондитерские изделия
((SELECT ID FROM ProduktGroup WHERE Name = 'Кондитерские изделия'), 'Концентрат киселя'),
-- Напитки
((SELECT ID FROM ProduktGroup WHERE Name = 'Напитки'), 'Чай'),
-- Специфические ингредиенты
((SELECT ID FROM ProduktGroup WHERE Name = 'Специфические ингредиенты'), 'Сахар'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Специфические ингредиенты'), 'Сухофрукты');

-- Добавление новых продуктов в таблицу Product
INSERT INTO Product (ProduktGroupId, Name) VALUES
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Мука II сорт'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Мясные изделия'), 'Мясо отварное'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупа рисовая'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупы'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Мука');




-- Добавление новых категорий
INSERT INTO ProduktGroup (Name) VALUES ('Рыбные изделия');

-- Добавление новых продуктов
INSERT INTO Product (ProduktGroupId, Name) VALUES
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Маргарин'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Специфические ингредиенты'), 'Вода'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Рыбные изделия'), 'Рыба с/м'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Соус'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Лавровый лист'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Специфические ингредиенты'), 'ТСМ'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Специфические ингредиенты'), 'Яйцо');



-- Добавление новых категорий
INSERT INTO ProduktGroup (Name) VALUES ('Соленья'), ('Маринованные овощи'), ('Бульоны');

-- Добавление новых продуктов
INSERT INTO Product (ProduktGroupId, Name) VALUES
((SELECT ID FROM ProduktGroup WHERE Name = 'Соленья'), 'Огурцы соленые'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Соленья'), 'Томаты соленые'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Маринованные овощи'), 'Свекла маринованная'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Маринованные овощи'), 'Капуста маринованная'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупа гречневая'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупа пшеничная'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупа ячневая'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Бульоны'), 'Мясной бульон'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Горчица');


-- Добавление новых продуктов
INSERT INTO Product (ProduktGroupId, Name) VALUES
((SELECT ID FROM ProduktGroup WHERE Name = 'Бульоны'), 'Мясной бульон'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Крупы и макаронные изделия'), 'Крупа пшено'),
((SELECT ID FROM ProduktGroup WHERE Name = 'Приправы и добавки'), 'Горчица');

