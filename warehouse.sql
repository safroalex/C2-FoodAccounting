-- Таблица "Склад" (Warehouse)
CREATE TABLE Warehouse (
    ID UUID PRIMARY KEY,
    QuantityProduktId UUID, 
    DateDeposit DATE,
    Remark TEXT
);

-- Таблица "Количество продовольствия" (QuantityProdukt)
CREATE TABLE QuantityProdukt (
    ID UUID PRIMARY KEY,
    WarehouseId UUID REFERENCES Warehouse(ID),
    ProduktGroupId UUID REFERENCES ProduktGroup(ID), 
    ProduktId UUID REFERENCES Produkt(ID), 
    Number VARCHAR(5),
    UnitId UUID REFERENCES Unit(ID), 
    DateExpiry DATE,
    Remark TEXT
);
-- Справочник "Группы продуктов" (ProduktGroup)
CREATE TABLE ProduktGroup (
    ID UUID PRIMARY KEY,
    Name VARCHAR(100)
);
-- Таблица "Единицы измерения" (Unit)
CREATE TABLE Unit (
    ID UUID PRIMARY KEY,
    Name VARCHAR(100),
    Description TEXT
);

