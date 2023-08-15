import redis

# Redis 연결 풀 설정
pool = redis.ConnectionPool(host='rhljh201.codns.com', port=6379, db=0, decode_responses=True)
redis_conn = redis.Redis(connection_pool=pool)
