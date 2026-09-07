const {test,expect}=require('@playwright/test');
test('external BOM consumer renders, handles input and loads matched assets',async({page})=>{
 const errors=[];page.on('pageerror',e=>errors.push(e.message));
 await page.goto('./');await expect(page.locator('body')).toHaveAttribute('data-ready','true');
 await page.locator('#name input').fill('Independent app');await page.locator('#greet').click();
 await expect(page.locator('body')).toHaveAttribute('data-greeting','Hello Independent app');
 await expect(page.locator('.dui-calendar')).toBeVisible();
 const css=await page.request.get('domino-widgets/css/domino-ui/domino-ui.css');expect(css.ok()).toBeTruthy();
 expect(errors).toEqual([]);
});
