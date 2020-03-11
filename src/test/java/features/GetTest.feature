#language: ru
  Функциональность: Получение валидного ответа

    @HTML
    Структура сценария: Отправляем запрос и получаем ответ в формате html
      Когда отправлен запрос на <Количество> <тип> как html
      Тогда проверяем получение <Количество> <тип> как html
      Примеры:
        | Количество  |     тип     |
        | 1           | "paragraph" |
        | 1           | "sentence"  |
        | 100         | "title"     |

    @JSON
    Структура сценария: Отправляем запрос и получаем ответ в формате json
      Когда отправлен запрос на <Количество> <тип> как json
      Тогда проверяем получение <Количество> <тип> как json
      Примеры:
        | Количество  |     тип     |
        | 1           | "paragraph" |
        | 1           | "sentence"  |
        | 100         | "title"     |

      @HTML
        @Negative
     Структура сценария: Отправляем запрос с слишком большим количеством
       Когда отправлено слишком большое <Количество> <Тип> в формате html
       Тогда получаем <Ошибка> в ответе
       Примеры:
         | Тип | Количество |                    Ошибка                            |
         | "paragraph" | 101 | "You requested too much content. Be more moderate." |
         | "sentence"  | 501 | "You requested too much content. Be more moderate." |
         | "title"     | 501 | "You requested too much content. Be more moderate." |
         | "paragraph" | -1 | "Unknown error. Contact the administration." |
         | "title"     | -2 | "Unknown error. Contact the administration." |
         | "sentence"  | -1 | "Unknown error. Contact the administration." |
#      С последним примером в этом блоке найден возможный баг. При указании отрицательного числа я ожидаю ошибку вида
#      Unknown error. Contact the administration. с кодом 31, но по итогу получаю просто пустой параграф.
#      Не совсем понял является ли это багом или так задумано.

    @JSON
      @Negative
    Структура сценария: Отправляем запрос с слишком большим количеством в формате json
      Когда отправлено слишком большое <Количество> <Тип> в формате json
      Тогда получаем <Ошибка> в статусе c <Код>
      Примеры:
        | Тип | Количество |                    Ошибка                            | Код |
        | "paragraph" | 101 | "You requested too much content. Be more moderate." | 11  |
        | "sentence"  | 501 | "You requested too much content. Be more moderate." | 11  |
        | "title"     | 501 | "You requested too much content. Be more moderate." | 11  |
        | "paragraph" | -1 | "Unknown error. Contact the administration."         | 31  |
        | "sentence"  | -1 | "Unknown error. Contact the administration."         | 31  |
        | "title"     | -2 | "Unknown error. Contact the administration."         | 31  |
