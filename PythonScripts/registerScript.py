import requests
import json
import sys


error = False


def login(username, password):
    user = {'username': username, 'password': password}
    response = requests.post('http://localhost:8080/login', json=user)
    if response.status_code == 403:
        print('Could not log in with following credentials:\n' + 'Username: ' + username + ' Password: ' + password + '\n')
        return ''
    return response.headers['Authorization']


def read_and_send(username, password, json_path):
    global error
    if error is False:
        token = ''
        data = {}
        folder = ''
        try:
            folder = sys.argv[1]
            with open(folder + '\\' + json_path, 'r') as file:
                data = json.load(file)
            token = login(username, password)
            if token != '':
                headers = {
                    'Authorization': token
                }
                response = requests \
                    .post('http://localhost:8080/api/administration/register-services', json=data, headers=headers)
                print('Successfully registered services for ' + username + '.')
            else:
                print('Register unsuccessful for ' + username + '.')
        except IndexError:
            error = True
            print('No root folder found! Run the script like: py registerScript.py <folder_dest>')


def main_method():
    read_and_send('students', 'test123', 'studentsService.json')
    read_and_send('heavyClient', 'test123', 'heavyClientService.json')
    read_and_send('securityFilter', 'test123', 'securityService.json')
    # read_and_send('securityFilter', 'test123', 'mongoService.json')
    # read_and_send('securityFilter', 'test123', 'transformatorService.json')
    print('Done.')


main_method()
