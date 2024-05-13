-- Таблица раскладок
CREATE TABLE Layout (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    DateBegin DATE,
    DateEnd DATE,
    Status VARCHAR(50)
);

-- Таблица блюд
CREATE TABLE Dish (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    Name VARCHAR(100),
    CaloricContent INT
);

-- Таблица ингредиентов блюда
CREATE TABLE DishIngredients (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    DishId UUID REFERENCES Dish(ID),
    ProduktId UUID REFERENCES Product(ID),
    Quantity DECIMAL,
    UnitId UUID REFERENCES Unit(ID)
);

-- Таблица блюд в раскладке
CREATE TABLE LayoutDishes (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    LayoutId UUID REFERENCES Layout(ID),
    DishId UUID REFERENCES Dish(ID),
    Quantity INT
);
