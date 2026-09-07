const {test,expect}=require('@playwright/test');
const pages=require('../upstream/showcase-lock.json').sources;
for(const sample of pages){
 test('gallery renders: '+sample.route,async({page})=>{
  const failedResources=[];page.on('response',response=>{if(response.status()>=400&&!response.url().endsWith('/favicon.ico'))failedResources.push(response.status()+' '+response.url());});
  const errors=[];page.on('pageerror',e=>(errors.push(e.message),console.error(e.stack)));
  await page.goto('?page='+sample.route);
  await expect(page.locator('#gallery-examples')).toHaveAttribute('data-ready','true');
  await expect(page.locator('#gallery-examples')).not.toBeEmpty();
  // Allow layout/observer and timer callbacks to execute through real browser turns.
  await page.evaluate(()=>new Promise(resolve=>requestAnimationFrame(()=>requestAnimationFrame(resolve))));
  await expect.poll(()=>page.locator("#gallery-examples img").evaluateAll(images=>images.filter(image=>image.src&&(!image.complete||!image.naturalWidth)).map(image=>image.src))).toEqual([]);
  expect(failedResources).toEqual([]);
  expect(errors).toEqual([]);
 });
}
