import logging
import sys

logging.basicConfig(
    format='%(asctime)s - [%(levelname)-7s] - %(name)s : %(message)s',
    # filename='/tmp/example.log',
    stream=sys.stdout,
    level=logging.DEBUG
)
logging.debug('This message should go to the log file')
logging.warning('Watch out!')  # will print a message to the console
logging.info('I told you so')  #

logger = logging.getLogger(__name__)
logger.info("hi~~~~")
