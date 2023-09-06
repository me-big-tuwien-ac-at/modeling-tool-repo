import geoip2.database
from apps.app.data import log_file


def get_visitor_ip_address(request):
    x_forwarded_for = request.META.get('HTTP_X_FORWARDED_FOR')

    if x_forwarded_for:
        ip = x_forwarded_for.split(',')[0]
    else:
        ip = request.META.get('REMOTE_ADDR')
    return ip


def log_ip_location(ip):
    reader = geoip2.database.Reader('./GeoLite2-City_20190430/GeoLite2-City.mmdb')
    response = reader.city(ip)

    print(response.country.name)
    print(response.country.names['zh-CN'])
    print(response.city.name)

    reader.close()
