const {test,expect} = require('@playwright/test');
test.beforeEach(async({page})=>{
 const errors=[];page.contractErrors=errors;page.on('pageerror',e=>{errors.push(e.message); console.error(e.stack);});
 await page.goto('./');
 await expect(page.locator('#screen')).toHaveAttribute('data-ready','true');
 expect(errors).toEqual([]);
});
test.afterEach(async({page})=>{expect(page.contractErrors || []).toEqual([]);});
test('button handler survives reattachment and can be removed',async({page})=>{
 await page.locator('#count').click();await expect(page.locator('#count')).toHaveText('Count 1');
 for(let i=0;i<3;i++) await page.locator('#reattach').click();
 await page.locator('#count').click();await expect(page.locator('#count')).toHaveText('Count 2');
 await page.locator('#remove-handler').click();await page.locator('#count').click();
 await expect(page.locator('#count')).toHaveText('Count 2');
});
test('required input validation',async({page})=>{
 await page.locator('#validate').click();
 await expect(page.locator('#name-field')).toHaveClass(/dui-field-invalid/);
 await page.locator('#name-field input').fill('Ada');await page.locator('#validate').click();
 await expect(page.locator('#name-field')).not.toHaveClass(/dui-field-invalid/);
});
test('original dialog opens and closes repeatedly',async({page})=>{
 for(let i=0;i<3;i++) {
 await page.locator('#open-dialog').click();await expect(page.locator('#close-dialog')).toBeVisible();
 await page.locator('#close-dialog').click();await expect(page.locator('#close-dialog')).not.toBeVisible();
 }
});
test('datatable renders and updates records',async({page})=>{
 await expect(page.locator('#records')).toContainText('Alpha');
 await expect(page.locator('#records')).toContainText('Beta');
 await page.locator('#update-rows').click();
 await expect(page.locator('#records')).toContainText('Gamma');
 await expect(page.locator('#records')).not.toContainText('Alpha');
});
test('input emits value changes',async({page})=>{
 await page.locator('#name-field input').fill('Grace');
 await page.locator('#validate').click();
 await expect(page.locator('#screen')).toHaveAttribute('data-value','Grace');
});
test('datatable supports selection and filtering',async({page})=>{
 await page.locator('#select-first').click();
 await expect(page.locator('#screen')).toHaveAttribute('data-selected','true');
 await page.locator('#filter-rows').click();
 await expect(page.locator('#records tbody tr').filter({hasText:'Beta'})).not.toBeVisible();
 await expect(page.locator('#records tbody tr').filter({hasText:'Alpha'})).toBeVisible();
 await page.locator('#clear-filter').click();
 await expect(page.locator('#records tbody tr').filter({hasText:'Beta'})).toBeVisible();
});
test('dialog handles Escape using original keyboard behavior',async({page})=>{
 await page.locator('#open-dialog').click();
 await expect(page.locator('#close-dialog')).toBeVisible();
 await page.locator('#close-dialog').focus();
 await page.keyboard.press('Escape');
 await expect(page.locator('#close-dialog')).not.toBeVisible();
});
test('all four widgets remain usable after repeated screen attachment',async({page})=>{
 for(let i=0;i<3;i++) await page.locator('#reattach-screen').click();
 await page.locator('#count').click();await expect(page.locator('#count')).toHaveText('Count 1');
 await page.locator('#name-field input').fill('Reattached');
 await page.locator('#validate').click();
 await expect(page.locator('#screen')).toHaveAttribute('data-value','Reattached');
 // Safari does not focus buttons on pointer click. Establish the focus origin explicitly.
 await page.locator('#open-dialog').focus();
 await page.locator('#open-dialog').click();await expect(page.locator('#close-dialog')).toBeVisible();
 await page.locator('#close-dialog').click();await expect(page.locator('#open-dialog')).toBeFocused();
 await page.locator('#update-rows').click();await expect(page.locator('#records')).toContainText('Gamma');
});

test('original showcase buttons render sizes, disabled states and groups',async({page},testInfo)=>{
 await page.goto('?page=buttons');
 await expect(page.locator('#gallery-examples')).toHaveAttribute('data-ready','true');
 await expect(page.locator('#gallery-examples')).toContainText('BUTTON SIZES');
 await expect(page.locator('#gallery-examples')).toContainText('DISABLED BUTTONS');
 await expect(page.locator('#gallery-examples button').first()).toBeVisible();
 await page.screenshot({path:testInfo.outputPath('gallery.png'),fullPage:true});
});

test('original showcase form examples accept text and render textarea',async({page})=>{
 await page.goto('?page=forms');
 await expect(page.locator('#gallery-examples')).toHaveAttribute('data-ready','true');
 const input=page.locator('#gallery-examples input:not([disabled]):not([readonly])').first();
 await input.fill('Shared example');await expect(input).toHaveValue('Shared example');
 await expect(page.locator('#gallery-examples textarea').first()).toBeVisible();
});

test('original showcase message dialog opens and dismisses',async({page})=>{
 await page.goto('?page=dialogs');
 await expect(page.locator('#gallery-examples')).toHaveAttribute('data-ready','true');
 await page.getByText('CLICK ME',{exact:true}).first().click();
 await expect(page.getByText('You have just opened a message dialog.',{exact:true}).first()).toBeVisible();
 await page.getByRole('button',{name:'Ok',exact:true}).click();
 await expect(page.getByText('You have just opened a message dialog.',{exact:true}).first()).not.toBeVisible();
});
