RESTAURANT SHELL PROJECT
========================

Что это:
Android-приложение-оболочка для сайтов ресторанов.

Что уже есть:
- стартовый экран «Выберите ресторан»
- 8 кнопок ресторанов
- открытие сайта внутри приложения
- кнопка «Сменить ресторан»
- кнопка «Обновить»
- pull-to-refresh
- обработка кнопки «Назад»
- GitHub Actions для автоматической сборки APK

Где менять ссылки ресторанов:
app/src/main/java/com/polihov/restaurantshell/RestaurantRepository.kt

Где менять логотипы:
app/src/main/res/drawable/
Сейчас стоят XML-заглушки. Можно заменить на PNG с теми же именами.

КАК СОБРАТЬ APK ЧЕРЕЗ GITHUB ACTIONS
------------------------------------
1. Создай новый репозиторий на GitHub.
2. Загрузи в него все файлы этого проекта.
3. Открой вкладку Actions.
4. Выбери workflow: Build Android APK.
5. Нажми Run workflow.
6. После завершения открой запуск workflow.
7. Внизу в Artifacts скачай архив restaurant-shell-debug-apk.
8. Внутри будет готовый файл app-debug.apk.

ЛОКАЛЬНАЯ СБОРКА В ANDROID STUDIO
---------------------------------
1. Открой проект в Android Studio.
2. Дождись Gradle Sync.
3. Нажми Run или Build APK.

ВАЖНО
-----
- Для GitHub Actions используется Gradle 8.7 прямо в workflow.
- Если захочешь release APK или AAB для публикации, надо будет добавить подпись приложения.
