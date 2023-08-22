import redis

# Redis 연결 풀 설정
pool = redis.ConnectionPool(host='49.50.161.125', port=6379, db=0, decode_responses=True)
redis_conn = redis.Redis(connection_pool=pool)
