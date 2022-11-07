import unittest
from app import server

class FlaskTest(unittest.TestCase):

    #check for response 200

    #signup
    def test_index_signup(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/signup')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)

    #verify-user
    def test_index_verifyuser(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/verify-user')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #update
    def test_index_update(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/update')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #search
    def test_index_search(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/search')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)

    #onborad-user
    def test_index_onboardusers(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/onboard-users')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #update active status
    def test_index_updateactiveusers(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/update/active/status')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)

    #login
    def test_index_login(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/login')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #logout
    def test_index_logout(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/logout')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #api-key-search
    def test_index_apiKeySearch(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/api-key-search')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #forgot-password
    def test_index_forgotPassword(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/forgot-password')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #reset-password
    def test_index_resetPassword(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/reset-password')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #get-user-roles
    def test_index_getRoles(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/get-roles')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)
    
    #verify-token
    def test_index_verifyToken(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/get/token/status')
        statuscode = response.status_code
        self.assertEqual(statuscode, 200)

    
    

    #check if returned is application/json
    def test_index_content_signup(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/signup')
        self.assertEqual(response.content_type,"application/json")

    def test_index_content_verifyuser(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/verify-user')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_update(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/update')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_search(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/search')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_onboard_users(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/onboard-users')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_updateactiveusers(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/update/active/status')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_login(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/login')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_logout(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/logout')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_login(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/login')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_apiKeySearch(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/api-key-search')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_forgotPassword(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/forgot-password')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_resetPassword(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/reset-password')
        self.assertEqual(response.content_type,"application/json")

    def test_index_content_getRoles(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/get-roles')
        self.assertEqual(response.content_type,"application/json")
    
    def test_index_content_verifyToken(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/get/token/status')
        self.assertEqual(response.content_type,"application/json")



    #check the returned data
    def test_index_data_signup(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/signup')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_verifyuser(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/verify-user')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_update(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/update')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_search(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/search')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_onboard_users(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/onboard-users')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_updateactiveusers(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/update/active/status')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_login(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/login')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_logout(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/logout')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_apiKeySearch(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/api-key-search')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_forgotPassword(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/forgot-password')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_resetPassword(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/reset-password')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_getRoles(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/get-roles')
        self.assertTrue(b'data' in response.data)
    
    def test_index_data_verifyToken(self):
        tester = server.test_client(self)
        response = tester.get('/v1/users/get/token/status')
        self.assertTrue(b'data' in response.data)