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







-- Добавление блюд
INSERT INTO Dish (Name, CaloricContent) VALUES
('Каши вязкие', 215),
('Каша молочная', 200),
('Кисель из концентрата', 88),
('Макаронные изделия отварные', 200),
('Мясо отварное для вторых блюд', 290),
('Пюре картофельное', 510),
('Сосиски отварные', 260),
('Суп картофельный на мясокостном бульоне с крупой рисовой', 260),
('Рагу из овощей', 150);

-- Каши вязкие
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Каши вязкие'), (SELECT ID FROM Product WHERE Name = 'Крупа'), 55, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каши вязкие'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каши вязкие'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каши вязкие'), (SELECT ID FROM Product WHERE Name = 'Кулинарные жиры'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Каша молочная
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Каша молочная'), (SELECT ID FROM Product WHERE Name = 'Молоко'), 100, (SELECT ID FROM Unit WHERE Name = 'миллилитры')),
((SELECT ID FROM Dish WHERE Name = 'Каша молочная'), (SELECT ID FROM Product WHERE Name = 'Крупы'), 40, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Кисель из концентрата
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Кисель из концентрата'), (SELECT ID FROM Product WHERE Name = 'Концентрат киселя'), 25, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Макаронные изделия отварные
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Макаронные изделия отварные'), (SELECT ID FROM Product WHERE Name = 'Макаронные изделии'), 49, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Мясо отварное для вторых блюд
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Мясо отварное для вторых блюд'), (SELECT ID FROM Product WHERE Name = 'Мясо сырое'), 90, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Мясо отварное для вторых блюд'), (SELECT ID FROM Product WHERE Name = 'Соль'), 1, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Сосиски отварные
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Сосиски отварные'), (SELECT ID FROM Product WHERE Name = 'Сосиски'), 80, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Суп картофельный на мясокостном бульоне с крупой рисовой
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Суп картофельный на мясокостном бульоне с крупой рисовой'), (SELECT ID FROM Product WHERE Name = 'Мука II сорт'), 2, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Суп картофельный на мясокостном бульоне с крупой рисовой'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Суп картофельный на мясокостном бульоне с крупой рисовой'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Суп картофельный на мясокостном бульоне с крупой рисовой'), (SELECT ID FROM Product WHERE Name = 'Томатная паста'), 1, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Суп картофельный на мясокостном бульоне с крупой рисовой'), (SELECT ID FROM Product WHERE Name = 'Кулинарные жиры'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Суп картофельный на мясокостном бульоне с крупой рисовой'), (SELECT ID FROM Product WHERE Name = 'Крупа рисовая'), 15, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Рагу из овощей
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Рагу из овощей'), (SELECT ID FROM Product WHERE Name = 'Картофель'), 50, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рагу из овощей'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 30, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рагу из овощей'), (SELECT ID FROM Product WHERE Name = 'Свекла'), 30, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рагу из овощей'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 15, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рагу из овощей'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 20, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рагу из овощей'), (SELECT ID FROM Product WHERE Name = 'Мука'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Пюре картофельное
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Пюре картофельное'), (SELECT ID FROM Product WHERE Name = 'Кулинарные жиры'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Пюре картофельное'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Пюре картофельное'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 15, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Пюре картофельное'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 20, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Пюре картофельное'), (SELECT ID FROM Product WHERE Name = 'Томатная паста'), 1, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Пюре картофельное'), (SELECT ID FROM Product WHERE Name = 'Мясо отварное'), 90, (SELECT ID FROM Unit WHERE Name = 'грамм'));




-- Добавление блюд
INSERT INTO Dish (Name, CaloricContent) VALUES
('Огурцы соленые с луком', 35),
('Томаты соленые с луком', 30),
('Салат из капусты свежей с морковью и луком', 45),
('Салат из моркови свежей с луком', 40),
('Салат из свеклы отварной с луком и морковью (до 1 января)', 60),
('Салат из свеклы отварной с луком и морковью (с 1 января)', 58),
('Салат из свеклы маринованной с луком и морковью маринованной', 50),
('Салат из свеклы маринованной с луком и капустой маринованной', 55),
('Каша гречневая вязкая', 150),
('Каша пшеничная молочная', 180),
('Каша ячневая молочная', 160),
('Суп картофельный с пшеном', 120);

