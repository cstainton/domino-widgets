const {test,expect}=require('@playwright/test');
test('published Elemental2 logger works without widget dependencies',async({page})=>{
 const messages=[],errors=[];
 page.on('console',m=>messages.push(m.text()));page.on('pageerror',e=>errors.push(e.message));
 await page.goto('./');
 await expect(page.locator('body')).toHaveAttribute('data-reuse','ready');
 expect(errors).toEqual([]);
 expect(messages.some(m=>m.includes('compat-reuse-success'))).toBe(true);
});
