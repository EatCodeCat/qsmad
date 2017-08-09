import sqlite3

conn = sqlite3.connect('qsm.db')
cursor = conn.cursor()

if __name__ == '__main__':
    cursor.execute('''
            CREATE TABLE task(
               id            INTeger  PRIMARY KEY autoincrement NOT NULL,
               name         VARCHAR(50) NULL,
               result       VARCHAR(100) NULL,
               key          VARCHAR(100) NULL,
               gn_id_list   TEXT NULL,
               status       VARCHAR(20) null,
               exec_time    TEXT NULL,
               log          TEXT NULL,
               h             int null,
               m            int null,
               s           int null
           );''')
