-- Таблица "Раскладка"
CREATE TABLE Layout (
    ID UUID PRIMARY KEY,
    TechnologicalCardId UUID REFERENCES TechnologicalCard(ID),
    Number INTEGER,
    DateBegin DATE,
    Date DATE,
    DateEnd DATE,
    CategoryPersonsId UUID REFERENCES CategoryPersons(ID),
    WeekId UUID REFERENCES Week(ID),
    TypeFoodId UUID REFERENCES TypeFood(ID),
    TypesDishId UUID REFERENCES TypesDish(ID),
    Calorific1 INTEGER,
    Calorific2 INTEGER,
    Calorific3 INTEGER,
    Calorific4 INTEGER,
    Calorific5 INTEGER
);

-- Справочник "Ингредиенты"
CREATE TABLE Ingredients (
    ID UUID PRIMARY KEY,
    TechnologicalCardId UUID REFERENCES TechnologicalCard(ID),
    ProduktId UUID REFERENCES Produkt(ID),
    WeightGross FLOAT,
    WeightNet FLOAT
);

-- Таблица "Количество продовольствия"
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

-- Таблица "Справка о спецконтингенте"
CREATE TABLE Convict (
    Id UUID PRIMARY KEY,
    QuantityConvictId UUID REFERENCES QuantityConvict(ID),
    AdditionDocId UUID REFERENCES AdditionDoc(ID),
    Number INTEGER,
    Date DATE,
    All INTEGER,
    IsAddition BOOLEAN
);

-- Справочник "Технологическая карта"
CREATE TABLE TechnologicalCard (
    ID UUID PRIMARY KEY,
    IngredientsId UUID REFERENCES Ingredients(ID),
    Name TEXT,
    ShortName TEXT,
    TypesDishId UUID REFERENCES TypesDish(ID),
    WeightOfDishes FLOAT,
    Calorific INTEGER,
    Protein FLOAT,
    Fat FLOAT,
    Carbohydrates FLOAT,
    Preparation TEXT,
    OrganolepticCharacteristic TEXT,
    Requirement TEXT
);
-- Справочник "Продукты" (Produkt)
CREATE TABLE Produkt (
    ID UUID PRIMARY KEY,
    ProduktGroupID UUID REFERENCES ProduktGroup(ID),
    Name VARCHAR,
    Mark BOOLEAN,
    Replacement BOOLEAN,
    Stomach BOOLEAN
);

-- Справочник "Категории спецконтингента" (CategoryPersons)
CREATE TABLE CategoryPersons (
    ID UUID PRIMARY KEY,
    Name VARCHAR,
    Mark INTEGER
);

