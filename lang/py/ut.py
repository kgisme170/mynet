import unittest
class mytest(unittest.TestCase):
    def __init__(self):
        print '__init__'
    def setUp(self):
        print 'setup'
        pass
    def tearDown(self):
        print 'tearDown'
        pass
    def testTrue(self):
        self.assertTrue(True)
if __name__=="__main__":
    unittest.main()