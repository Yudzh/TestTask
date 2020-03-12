#language: ru
Функциональность: Получение валидного ответа в формате JSON

  @JSON
  Структура сценария: Отправляем запрос и получаем ответ в формате json
    Когда отправлен запрос на <Количество> <тип> как <формат>
    Тогда проверяем получение <Количество> <тип> как json
    Примеры:
      | Количество | тип         | формат |
      | 1         | "paragraph" | "json" |
      | 1         | "sentence"  | "json" |
      | 100         | "sentence"  | "json" |
      | 500        | "title"     | "json" |