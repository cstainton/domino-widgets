"""Loopback-only fixture server with a real multipart upload echo endpoint."""
from http.server import ThreadingHTTPServer, SimpleHTTPRequestHandler
from pathlib import Path
import functools,json,os

class Fixtures(SimpleHTTPRequestHandler):
    def do_POST(self):
        if not self.path.endswith('/service/upload'):
            self.send_error(404);return
        size=int(self.headers.get('Content-Length','0'))
        if size>1048576:
            self.send_error(413);return
        body=self.rfile.read(size)
        data=json.dumps({'method':'POST','contentType':self.headers.get('Content-Type'),'body':body.decode('utf-8')}).encode()
        self.send_response(200);self.send_header('Content-Type','application/json');self.send_header('Content-Length',str(len(data)));self.end_headers();self.wfile.write(data)

if __name__=='__main__':
    ThreadingHTTPServer(('127.0.0.1',8791),functools.partial(Fixtures,directory=os.environ.get('DOMINO_FIXTURE_ROOT',str(Path(__file__).resolve().parents[1])))).serve_forever()
