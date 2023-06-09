# DiplomaAppComponents
Этот проект - набор программных компонент для создания Android-приложения, разработанный в рамках ВКР.

Для этого проекта написана [документация](https://github.com/EvilEgoist/DiplomaAppComponents/blob/master/Documentation.md#это-набор-компонентов-для-android-приложения-разработанный-в-рамках-вкр)

# Демонстрация UI

## Главный экран
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/ed0d14e1-a716-4c31-b8fa-8d08ae0330ee

Используется пагинация данных, изображения и их ключи страниц сохраняются в БД

Для главного экрана используются компоненты:
- `AnimatedAsyncImage` обертка над `AsyncImage`, добавляющая плейсхолдер во время загрузки изображений и анимацию появления.
- `UnsplashRemoteMediator` - кастомный `RemoteMediator` для пагинации, использующий Paging3, постраничное сохранение ключей для страниц и изображений в БД.
- `CircleRevealPager` для анимации постраничного пееключения изображений
- `SortOrderMenu` для изменения варианта сортировки
- `AnimatedBottomNavigation` - нижняя панель навигации

## Экран поиска

https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/c5e16cb1-60de-4204-97c1-b0319f5aea97

Функционал аналогичен главному экрану. Но изображения не сохраняются в БД.

Для экрана экрана поиска используются компоненты:
- `SwipeActions` (поисковое предложение) для добавления кастомного `SwipeToDismiss`. Свайп можно настроить на 2 направления, которые могут работать одновременно (start-to-end, end-to-start)
- Настроенный `SearchBar` 
- Для рекомендации подсказок используется Room и Flow
- `AnimatedAsyncImage` - для оторбражения изображений
- `SearchPagingSource` - кастомный `PagingSource` для пагинации данных без сохранения изображений в бд
- `SortOrderMenu`

## Экран детальной информации

https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/428e6a9d-d6c8-410b-8d56-c45835bb44cc

Для этого экрана используются:
- `AnimatedAsyncImage`
- Intent для открытия браузера.

## Другие UI-компоненты

### FluidButton
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/cb782107-cee9-4fc4-8db3-c3b91e76af1b

### FlipCard
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/aca4d4e8-85e4-41ea-ac63-00b480494f1c

### DraggableCards
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/27342423-a714-4261-b503-fab450e3d1a3

### LoginScreen
https://github.com/EvilEgoist/DiplomaAppComponents/assets/79916148/730277c5-7cf2-4b32-969d-69b0a2687787
