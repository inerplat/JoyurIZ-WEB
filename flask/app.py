from joyuriz import app
import logging
import sys

handler = logging.StreamHandler(sys.stdout)
app.logger.addHandler(handler)
app.run(debug=False, host='0.0.0.0')
