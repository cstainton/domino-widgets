const {test,expect}=require('@playwright/test');
test('native storage, promises, blob fetch, SVG, locale labels and file reader',async({page})=>{
 const errors=[];page.on('pageerror',e=>errors.push(e.message));
 await page.goto('?page=browser-apis');const root=page.locator('#browser-apis');
 await expect(root).toHaveAttribute('data-storage','passed');
 await expect(root).toHaveAttribute('data-date-parse','passed');
 await expect(root).toHaveAttribute('data-spanish','enero');
 await expect(root).toHaveAttribute('data-arabic',/يناير/);
 await expect(root).toHaveAttribute('data-promise','PROMISE-VALUE');
 await expect(root).toHaveAttribute('data-rejection','rejected-value');
 await expect(root).toHaveAttribute('data-blob','blob-value');
 await expect(root.locator('svg rect')).toHaveAttribute('fill','#4466cc');
 await page.locator('#native-upload').setInputFiles({name:'sample.txt',mimeType:'text/plain',buffer:Buffer.from('uploaded text')});
 await expect(root).toHaveAttribute('data-file','uploaded text');
 await page.locator('#push-history').click();await expect(page).toHaveURL(/#detail$/);
 await page.goBack();await expect(root).toHaveAttribute('data-history','back');
 expect(errors).toEqual([]);
});
