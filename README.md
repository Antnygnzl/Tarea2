Instrucciones de Uso - Sistema de Pruebas Taxonomía de Bloom
Cómo usar la aplicación

1. Ejecuta la aplicación desde el archivo principal (PruebaWindow).

2. Responde las preguntas seleccionando una opción y navega con los botones Anterior y Siguiente.

3. Al finalizar, presiona Enviar respuestas para ver la revisión.

4. En la ventana de revisión puedes:

- Ver cada pregunta con la respuesta correcta y la tuya marcada.

- Navegar entre preguntas con Anterior y Siguiente.

- Volver al resumen general con el botón Ver resumen.

5. El resumen muestra:

- Porcentaje de respuestas correctas por nivel de Bloom.

- Porcentaje de respuestas correctas por tipo de ítem.

--------------------------------------------------------------------------------------------

Formato del archivo CSV para cargar ítems (items_tarea2.csv)
El archivo CSV debe tener las siguientes columnas (con encabezado):

tipo, enunciado, opciones, nivel, tiempo, respuesta

Descripción de las columnas:
tipo: Tipo de ítem, por ejemplo "opcion_multiple" o "verdadero_falso".

enunciado: Texto de la pregunta.

opciones:

Para opcion_multiple: Lista separada por ; con las alternativas. Ej: Rojo;Verde;Azul;Amarillo

Para verdadero_falso: Debe ir vacío o como NaN.

nivel: Nivel de la Taxonomía de Bloom. Ej: "Recordar", "Comprender", "Aplicar", "Analizar", etc.

tiempo: Tiempo estimado para responder la pregunta (en segundos).

respuesta: Texto exacto de la respuesta correcta. En opción múltiple debe coincidir con una de las opciones.

Ejemplo:

tipo,enunciado,opciones,nivel,tiempo,respuesta
opcion_multiple,¿Cuál es la capital de Francia?,París;Londres;Roma;Madrid,Recordar,30,París
verdadero_falso,El Sol es una estrella.,,Comprender,15,Verdadero
