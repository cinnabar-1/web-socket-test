let userRelationsData = [];
let selectedItem;
let haveRelationsData = false;

function getText(doo) {
    console.log(doo.innerText);
    let listSelected = document.getElementById('list-selected');
    listSelected.innerText = doo.innerText;
    selectedItem = userRelationsData[doo.id];
    console.log(selectedItem);
}

// $(document).ready(function () {
//     let innerList = document.getElementById('inner-list');
//     let count = 0;
//     data.forEach(item => {
//         item.fakeId = count++;
//         let node = document.createElement('p');
//         node.innerText = item.name;
//         node.setAttribute('onclick', "getText(this)");
//         node.setAttribute('class', "list-select-item");
//         node.setAttribute('id', item.fakeId);
//         innerList.append(node);
//     });
// });
function initList() {
    let innerList = document.getElementById('inner-list');
    let count = 0;
    userRelationsData.forEach(item => {
        item.fakeId = count++;
        let node = document.createElement('p');
        node.innerText = item.name;
        node.setAttribute('onclick', "getText(this)");
        node.setAttribute('class', "list-select-item");
        node.setAttribute('id', item.fakeId);
        innerList.append(node);
    });
}