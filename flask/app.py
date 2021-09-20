from joyuriz import app
import logging
import sys

if __name__ == '__main__':
  handler = logging.StreamHandler(sys.stdout)
  app.logger.addHandler(handler)
  app.run(debug=False, host='0.0.0.0', port=80)
