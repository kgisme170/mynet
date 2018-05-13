import logging
logging.basicConfig(level=logging.DEBUG,  
                    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',  
                    datefmt='%a, %d %b %Y %H:%M:%S',  
                    filemode='w')
logging.debug('debug')
logging.info('info')
logging.warn('warn')
logging.error('error')