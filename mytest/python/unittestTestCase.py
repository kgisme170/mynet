import unittest
class mytest(unittest.TestCase):
    def __init__(self,methodName='runTest'):
        super(mytest,self).__init__(methodName)
        print '__init__'
    def setUp(self):
        print '----setUp'
    def tearDown(self):
        print '----tearDown'
    def testTrue1(self):
        print "true1 begin"
        self.assertTrue(True)
        print "true1 end"
    def testTrue2(self):
        print "true2 begin"
        self.assertTrue(True)
        print "true2 end"
if __name__=="__main__":
    unittest.main()