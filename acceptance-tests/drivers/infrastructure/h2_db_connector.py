import psycopg2

class H2DBConnector:
    def __init__(self,
                 dbname: str = "mem:devdb",  # Match your H2 DB name
                 host: str = "localhost",
                 port: int = 5435,
                 username: str = "sa",
                 password: str = "sa"):
        self.dbname = dbname
        self.host = host
        self.port = port
        self.username = username
        self.password = password
        self.conn = None
        self.cursor = None

    def connect(self):
        if self.conn is None:
            self.conn = psycopg2.connect(
                dbname=self.dbname,
                host=self.host,
                port=self.port,
                user=self.username,
                password=self.password
            )
            self.cursor = self.conn.cursor()
        return self.conn

    def execute(self, sql: str, params: tuple = None):
        if params is None:
            self.cursor.execute(sql)
        else:
            self.cursor.execute(sql, params)
        self.conn.commit()  # Commit changes for non-query operations

    def query(self, sql: str, params: tuple = None) -> list:
        if params is None:
            self.cursor.execute(sql)
        else:
            self.cursor.execute(sql, params)
        return self.cursor.fetchall()

    def close(self):
        if self.cursor is not None:
            self.cursor.close()
            self.cursor = None
        if self.conn is not None:
            self.conn.close()
            self.conn = None
