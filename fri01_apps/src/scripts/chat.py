import requests
import json
import sys

KEY = ['40d56dcf5e1d4edc8a891eb824a11437', '8afba6fdc75544f0bebc465615da1e0b'
    , '347b39ee228b4b109dae7270cc08d3c8', '4f3664efdfacd7f7968f72d2dd594113'
    , '84ddc5d9c1a94e55b1786987435364a2', 'ec3cff2c300048a2b11ed63c0180b3cd'
    , '13c611e3c80e4acf963a788c005a2f90', 'ae3ecf47d74a8904cc7e527eece961e6'
    , 'd8c6461d3956425e95b498534705554d', 'f35f9b106061e08300c90f79c3d83b69']


def turingChat(question):
    i = 0
    url = 'http://www.tuling123.com/openapi/api'
    req_info = question.encode('utf-8')
    result = ""
    while i < 10:
        query = {'key': KEY[i], 'info': req_info}
        headers = {'Content-type': 'text/html', 'charset': 'utf-8'}
        try:
            r = requests.get(url, params=query, headers=headers)
            res = r.text
            result = json.loads(res).get('text').replace('<br>', '\n')
            if result != '亲爱的，当天请求次数已用完。':
                break
            i = i + 1
        except:
            result = "网络未连接"
            break
    return result


print(turingChat(str(sys.argv[1:])))
