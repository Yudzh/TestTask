#language: ru
Функциональность: Получение ошибки запроса в формате JSON

  @JSON
    @Negative
  Структура сценария: Отправляем запрос с слишком большим количеством в формате json
    Когда отправлен запрос на <Количество> <тип> как <формат>
    Тогда получаем <Ошибка> в статусе c <Код>
    Примеры:
      | тип         | Количество | Ошибка                                              | Код | формат |
      | "paragraph" | 101        | "You requested too much content. Be more moderate." | 11  | "json" |
      | "sentence"  | 501        | "You requested too much content. Be more moderate." | 11  | "json" |
      | "title"     | 501        | "You requested too much content. Be more moderate." | 11  | "json" |
      | "paragraph" | -1         | "Unknown error. Contact the administration."        | 31  | "json" |
      | "sentence"  | -1         | "Unknown error. Contact the administration."        | 31  | "json" |
      | "title"     | -2         | "Unknown error. Contact the administration."        | 31  | "json" |