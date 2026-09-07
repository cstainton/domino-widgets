const {defineConfig}=require('@playwright/test');
const browsers=(process.env.BROWSERS||'chromium').split(',');
const projects=[];
for(const browserName of browsers){
 const use={browserName,locale:'en-US',timezoneId:'UTC'};
 if(browserName==='chromium')use.channel=process.env.PLAYWRIGHT_CHANNEL||'chromium';
 for(const backend of ['gwt','teavm'])projects.push({name:browserName==='chromium'?backend:backend+'-'+browserName,testMatch:['contracts.spec.js','gallery.spec.js','native.spec.js','interactions.spec.js'],use:{...use,baseURL:'http://127.0.0.1:8791/showcase-'+backend+'/target/site/'}});
 projects.push({name:browserName==='chromium'?'reuse':'reuse-'+browserName,testMatch:'reuse.spec.js',use:{...use,baseURL:'http://127.0.0.1:8791/compat-reuse-smoke/target/site/'}});
}
module.exports=defineConfig({workers:2,globalTimeout:600000,testDir:'.',testMatch:'*.spec.js',use:{screenshot:'only-on-failure',trace:'retain-on-failure',headless:true},reporter:[['list'],['json',{outputFile:'test-results/results.json'}]],webServer:{command:'python3 server.py',port:8791},projects});
