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
ALTER TABLE LayoutDishes ADD DayOfWeek VARCHAR(10);








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


-- Добавление блюд
INSERT INTO Dish (Name, CaloricContent) VALUES
('Котлета мясная', 220),
('Макароны откидные', 200),
('Рыба (Сельдь) соленая', 150),
('Рыба (Путассу) отварная', 180),
('Рыба (Путассу) запеченная в соусе', 210),
('Рыба жареная', 220),
('Поджарка из мяса', 250);


-- Рыба жареная
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Рыба жареная'), (SELECT ID FROM Product WHERE Name = 'Рыба с/м'), 100, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба жареная'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба жареная'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба жареная'), (SELECT ID FROM Product WHERE Name = 'Мука II сорт'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Поджарка из мяса
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Поджарка из мяса'), (SELECT ID FROM Product WHERE Name = 'Мясо сырое'), 90, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Поджарка из мяса'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Поджарка из мяса'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Поджарка из мяса'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Поджарка из мяса'), (SELECT ID FROM Product WHERE Name = 'Вода'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Рыба (Путассу) запеченная в соусе
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) запеченная в соусе'), (SELECT ID FROM Product WHERE Name = 'Рыба с/м'), 100, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) запеченная в соусе'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) запеченная в соусе'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) запеченная в соусе'), (SELECT ID FROM Product WHERE Name = 'Соус'), 25, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) запеченная в соусе'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Рыба (Путассу) отварная
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) отварная'), (SELECT ID FROM Product WHERE Name = 'Рыба с/м'), 100, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) отварная'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) отварная'), (SELECT ID FROM Product WHERE Name = 'Лавровый лист'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Рыба (Путассу) отварная'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Рыба (Сельдь) соленая
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Рыба (Сельдь) соленая'), (SELECT ID FROM Product WHERE Name = 'Рыба с/м'), 100, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Макароны откидные
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Макароны откидные'), (SELECT ID FROM Product WHERE Name = 'Макаронные изделии'), 70, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Макароны откидные'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Макароны откидные'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Макароны откидные'), (SELECT ID FROM Product WHERE Name = 'Вода'), 420, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Котлета мясная
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Мясо сырое'), 90, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'ТСМ'), 28, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Мука II сорт'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Яйцо'), 0.5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Вода'), 6.3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Котлета мясная'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Огурцы соленые с луком
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Огурцы соленые с луком'), (SELECT ID FROM Product WHERE Name = 'Огурцы соленые'), 55, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Огурцы соленые с луком'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Огурцы соленые с луком'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Томаты соленые с луком
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Томаты соленые с луком'), (SELECT ID FROM Product WHERE Name = 'Томаты соленые'), 55, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Томаты соленые с луком'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Томаты соленые с луком'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Салат из капусты свежей с морковью и луком
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Салат из капусты свежей с морковью и луком'), (SELECT ID FROM Product WHERE Name = 'Капуста'), 55, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из капусты свежей с морковью и луком'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из капусты свежей с морковью и луком'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из капусты свежей с морковью и луком'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из капусты свежей с морковью и луком'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Салат из моркови свежей с луком
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Салат из моркови свежей с луком'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из моркови свежей с луком'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 30, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из моркови свежей с луком'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из моркови свежей с луком'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Салат из свеклы отварной с луком и морковью (до 1 января)
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы отварной с луком и морковью (до 1 января)'), (SELECT ID FROM Product WHERE Name = 'Свекла'), 75, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы отварной с луком и морковью (до 1 января)'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы отварной с луком и морковью (до 1 января)'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы отварной с луком и морковью (до 1 января)'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы отварной с луком и морковью (до 1 января)'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Салат из свеклы маринованной с луком и морковью маринованной
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и морковью маринованной'), (SELECT ID FROM Product WHERE Name = 'Свекла'), 50, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и морковью маринованной'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и морковью маринованной'), (SELECT ID FROM Product WHERE Name = 'Морковь'), 50, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и морковью маринованной'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и морковью маринованной'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));

-- Салат из свеклы маринованной с луком и капустой маринованной
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и капустой маринованной'), (SELECT ID FROM Product WHERE Name = 'Свекла маринованная'), 50, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и капустой маринованной'), (SELECT ID FROM Product WHERE Name = 'Лук репчатый'), 10, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и капустой маринованной'), (SELECT ID FROM Product WHERE Name = 'Капуста маринованная'), 50, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и капустой маринованной'), (SELECT ID FROM Product WHERE Name = 'Масло растительное'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Салат из свеклы маринованной с луком и капустой маринованной'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Каша гречневая вязкая
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Каша гречневая вязкая'), (SELECT ID FROM Product WHERE Name = 'Крупа гречневая'), 70, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша гречневая вязкая'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 2.5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша гречневая вязкая'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша гречневая вязкая'), (SELECT ID FROM Product WHERE Name = 'Вода'), 224, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Каша пшеничная молочная
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Каша пшеничная молочная'), (SELECT ID FROM Product WHERE Name = 'Крупа пшеничная'), 80, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша пшеничная молочная'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша пшеничная молочная'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша пшеничная молочная'), (SELECT ID FROM Product WHERE Name = 'Вода'), 108, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша пшеничная молочная'), (SELECT ID FROM Product WHERE Name = 'Молоко'), 156, (SELECT ID FROM Unit WHERE Name = 'грамм'));


-- Каша ячневая молочная
INSERT INTO DishIngredients (DishId, ProduktId, Quantity, UnitId) VALUES
((SELECT ID FROM Dish WHERE Name = 'Каша ячневая молочная'), (SELECT ID FROM Product WHERE Name = 'Крупа ячневая'), 80, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша ячневая молочная'), (SELECT ID FROM Product WHERE Name = 'Маргарин'), 5, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша ячневая молочная'), (SELECT ID FROM Product WHERE Name = 'Молоко'), 100, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша ячневая молочная'), (SELECT ID FROM Product WHERE Name = 'Соль'), 3, (SELECT ID FROM Unit WHERE Name = 'грамм')),
((SELECT ID FROM Dish WHERE Name = 'Каша ячневая молочная'), (SELECT ID FROM Product WHERE Name = 'Вода'), 196, (SELECT ID FROM Unit WHERE Name = 'грамм'));

