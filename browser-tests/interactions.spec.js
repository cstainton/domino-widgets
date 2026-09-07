const {test,expect}=require('@playwright/test');
test.beforeEach(async({page})=>{page.errors=[];page.on('pageerror',e=>page.errors.push(e.message));});
test.afterEach(async({page})=>expect(page.errors).toEqual([]));
async function open(page,route){await page.goto('?page='+route);await expect(page.locator('#gallery-examples')).toHaveAttribute('data-ready','true');}
test('chip removal changes the original widget DOM',async({page})=>{
 await open(page,'chips');const chips=page.locator('.dui-chip');const before=await chips.count();
 await page.locator('.dui-chip-remove').first().click();await expect(chips).toHaveCount(before-1);
});
test('tabs switch visible content repeatedly',async({page})=>{
 await open(page,'tabs');const card=page.locator('.dui-card').first();
 await card.getByText('HOME',{exact:true}).click();await expect(card.getByText('Home Content',{exact:true})).toBeVisible();
 await card.getByText('SETTINGS',{exact:true}).click();await expect(card.getByText('Settings Content',{exact:true})).toBeVisible();
 await expect(card.getByText('Home Content',{exact:true})).not.toBeVisible();
});
test('calendar changes selected day and navigates months',async({page})=>{
 await open(page,'datepicker');const calendar=page.locator('.dui-calendar').first();
 await calendar.locator('.dui-calendar-day-number').filter({hasText:/^15$/}).first().click();
 await expect(calendar.locator('.dui-selected-date')).toContainText('15');
 const title=calendar.locator('.dui-calendar-selectors-month');const before=await title.textContent();
 await calendar.locator('.dui-calendar-selectors-next').click();await expect(title).not.toHaveText(before);
 await calendar.locator('.dui-calendar-selectors-previous').click();await expect(title).toHaveText(before);
});
test('numeric input accepts a changed value',async({page})=>{
 await open(page,'inputfields');const input=page.locator('#gallery-examples input').first();
 await input.fill('42');await input.press('Tab');await expect(input).toHaveValue('42');
});
test('original table data and selection remain usable',async({page})=>{
 await open(page,'table-selection-plugin');const table=page.locator('table').first();
 await expect(table).toContainText('Alice Example');const row=table.locator('tbody tr').filter({hasText:'Bob Example'}).first();
 await row.click();await expect(row).toHaveClass(/dui-datatable-row-selected/);
 await row.click();await expect(row).not.toHaveClass(/dui-datatable-row-selected/);
});
test('original table pagination changes records',async({page})=>{
 await open(page,'table-pagination-plugin');const card=page.locator('.dui-card').first();
 await expect(card).toContainText('Alice Example');
 await card.getByText('2',{exact:true}).last().click();await expect(card).not.toContainText('Alice Example');
 await card.getByText('1',{exact:true}).last().click();await expect(card).toContainText('Alice Example');
});
test('tree expands nested content and collapses it again',async({page})=>{
 await open(page,'tree');const tree=page.locator('.dui-tree').filter({has:page.locator('.mdi-desktop-classic')}).first();
 const label=tree.getByText('Computer',{exact:true});
 const node=label.locator('xpath=ancestor::li[1]');
 const children=node.locator(':scope > ul');
 const toggle=node.locator(':scope > a i').first();
 await expect(children).toHaveAttribute('dui-collapsed','true');
 await toggle.click();await expect(children).not.toHaveAttribute('dui-collapsed','true');
 await expect(children).not.toHaveCSS('height','0px');
 await children.evaluate(element=>Promise.all(element.getAnimations().map(animation=>animation.finished)));
 await toggle.click();await expect(children).toHaveAttribute('dui-collapsed','true');
 await expect(children).toHaveCSS('height','0px');
});
test('rich text editor accepts editing and resets its HTML value',async({page})=>{
 await page.goto('?page=richtext');const root=page.locator('#richtext-example');await expect(root).toHaveAttribute('data-ready','true');
 const editor=root.locator('[contenteditable=true]');await expect(editor).toContainText('Initial content');
 await editor.fill('Edited content');await page.locator('#read-html').click();await expect(root).toHaveAttribute('data-html',/Edited content/);
 await page.locator('#reset-editor').click();await expect(editor).toContainText('Reset content');
});

test('original upload sends multipart data and reports server success',async({page})=>{
 await open(page,'advanced-forms');
 const upload=page.locator('.dui-file-upload').nth(1);
 const received=page.waitForResponse(response=>response.url().endsWith('/service/upload')&&response.request().method()==='POST');
 await upload.locator('input[type=file]').setInputFiles({name:'contract.txt',mimeType:'text/plain',buffer:Buffer.from('domino upload contract')});
 const sent=await (await received).json();
 expect(sent.method).toBe('POST');
 expect(sent.contentType).toContain('multipart/form-data');
 expect(sent.body).toContain('domino upload contract');
 await expect(upload).toContainText('Upload completed.');
});
test('dynamic suggestions fetch and select a country',async({page})=>{
 await open(page,'advanced-forms');
 const field=page.locator('.dui-form-field').filter({hasText:'Suggested country'}).first();
 await field.locator('input').pressSequentially('United');
 await page.getByText('United Kingdom',{exact:true}).last().click();
 await expect(field.locator('input')).toHaveValue('United Kingdom');
});
