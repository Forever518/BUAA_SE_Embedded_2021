import requests


response = requests.get('http://www.weather.com.cn/data/cityinfo/101010100.html')
response.encoding = 'utf-8'
response_json = response.json()

result = response_json['weatherinfo']['city'] + ", " + response_json['weatherinfo']['weather'] + ", " \
     + response_json['weatherinfo']['temp1'][:-1] + "到" + response_json['weatherinfo']['temp2'][:-1] + "度。"
print(result)

# print(response_json['weatherinfo']['ptime'])
