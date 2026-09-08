const {defineConfig}=require('@playwright/test');
const path=require('node:path');
const sample=process.env.EXTERNAL_SAMPLE||path.resolve(__dirname,'../examples');
const browsers=(process.env.BROWSERS||'chromium').split(',');
module.exports=defineConfig({testDir:'.',testMatch:'external.spec.js',workers:2,reporter:[['list'],['json',{outputFile:'test-results/external.json'}]],use:{screenshot:'only-on-failure',trace:'retain-on-failure'},webServer:{command:'python3 -m http.server 8792 --bind 127.0.0.1 --directory '+JSON.stringify(sample),port:8792},projects:browsers.flatMap(browserName=>['teavm'].map(backend=>({name:backend+'-'+browserName,use:{browserName,baseURL:'http://127.0.0.1:8792/'+backend+'/target/site/'}})))});
