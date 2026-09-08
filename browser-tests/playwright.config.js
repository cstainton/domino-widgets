const {defineConfig}=require('@playwright/test');
const browsers=(process.env.BROWSERS||'chromium').split(',');
const projects=[];
const port=Number(process.env.DOMINO_PORT||8791);
for(const browserName of browsers){
 const use={browserName,locale:'en-US',timezoneId:'UTC'};
 if(browserName==='chromium')use.channel=process.env.PLAYWRIGHT_CHANNEL||'chromium';
 for(const backend of ['teavm'])projects.push({name:browserName==='chromium'?backend:backend+'-'+browserName,testMatch:['contracts.spec.js','gallery.spec.js','native.spec.js','interactions.spec.js'],use:{...use,baseURL:'http://127.0.0.1:'+port+'/showcase-'+backend+'/target/site/'}});
 projects.push({name:browserName==='chromium'?'reuse':'reuse-'+browserName,testMatch:'reuse.spec.js',use:{...use,baseURL:'http://127.0.0.1:'+port+'/compat-reuse-smoke/target/site/'}});
}
module.exports=defineConfig({outputDir:process.env.BROWSER_OUTPUT||'test-results',workers:2,globalTimeout:600000,testDir:'.',testMatch:'*.spec.js',use:{screenshot:'only-on-failure',trace:'retain-on-failure',headless:true},reporter:[['list'],['json',{outputFile:process.env.BROWSER_RESULTS||'test-results/results.json'}]],webServer:{command:'python3 server.py',port},projects});
