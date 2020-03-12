#language: ru
Функциональность: Получение ошибки запроса в формате HTML

  @HTML
    @Negative
  Структура сценария: Отправляем запрос с слишком большим количеством
    Когда отправлен запрос на <Количество> <тип> как <формат>
    Тогда получаем <Ошибка> в ответе
    Примеры:
      | тип         | Количество | Ошибка                                              | формат |
      | "paragraph" | 101        | "You requested too much content. Be more moderate." | "html" |
      | "sentence"  | 501        | "You requested too much content. Be more moderate." | "html" |
      | "title"     | 501        | "You requested too much content. Be more moderate." | "html" |
      | "paragraph" | -1         | "Unknown error. Contact the administration."        | "html" |
      | "title"     | -2         | "Unknown error. Contact the administration."        | "html" |
      | "sentence"  | -1         | "Unknown error. Contact the administration."        | "html" |
#      С последним примером в этом блоке найден возможный баг. При указании отрицательного числа я ожидаю ошибку вида
#      Unknown error. Contact the administration. с кодом 31, но по итогу получаю просто пустой параграф.
#      Не совсем понял является ли это багом или так задумано.