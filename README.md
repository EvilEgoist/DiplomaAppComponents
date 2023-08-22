# DiplomaAppComponents
Этот проект - набор программных компонент для создания Android-приложения, разработанный в рамках ВКР.

Для этого проекта написана [документация](https://github.com/EvilEgoist/DiplomaAppComponents/blob/master/Documentation.md#это-набор-компонентов-для-android-приложения-разработанный-в-рамках-вкр)

# Демонстрация UI

## Главный экран

https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/416387dd-9ed8-49ca-9ec8-6c8d111703e8

Используется пагинация данных, изображения и их ключи страниц сохраняются в БД

Для главного экрана используются компоненты:
- `AnimatedAsyncImage` обертка над `AsyncImage`, добавляющая плейсхолдер во время загрузки изображений и анимацию появления.
- `UnsplashRemoteMediator` - кастомный `RemoteMediator` для пагинации, использующий Paging3, постраничное сохранение ключей для страниц и изображений в БД.
- `CircleRevealPager` для анимации постраничного пееключения изображений
- `SortOrderMenu` для изменения варианта сортировки
- `AnimatedBottomNavigation` - нижняя панель навигации

## Экран поиска

https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/45a19bee-f765-4fac-a83f-7cc2f97c96d0

Функционал аналогичен главному экрану. Но изображения не сохраняются в БД.

Для экрана экрана поиска используются компоненты:
- `SwipeActions` (поисковое предложение) для добавления кастомного `SwipeToDismiss`. Свайп можно настроить на 2 направления, которые могут работать одновременно (start-to-end, end-to-start)
- Настроенный `SearchBar` 
- Для рекомендации подсказок используется Room и Flow
- `AnimatedAsyncImage` - для оторбражения изображений
- `SearchPagingSource` - кастомный `PagingSource` для пагинации данных без сохранения изображений в бд
- `SortOrderMenu`

## Экран детальной информации

https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/b26937d2-16f0-4417-b4a5-d75007c7733f

Для этого экрана используются:
- `AnimatedAsyncImage`
- Intent для открытия браузера.

## Экран афиши фильмов
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/ee31977f-cdbe-493f-b73e-3d4ad1c4c28f

## Другие UI-компоненты

### FluidButton

https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/b55e2ac0-4925-41c1-a6f9-0525a2ae410c

### FlipCard
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/696f4b96-de03-4b88-b8ff-6739ce430385

### DraggableCards
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/7d64c7c4-688a-4f3f-970c-ffbe633b8218

### LoginScreen
![login-screen](https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/a9164411-293e-4477-9415-e6e9cda80bcf)



