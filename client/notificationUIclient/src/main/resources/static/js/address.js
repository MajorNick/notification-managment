let addressCount = 0;

function addAddressFields() {
    try {
        const container = document.getElementById('addressesContainer');
        if (!container) {
            console.error("Container 'addressesContainer' not found");
            return;
        }

        const newAddressDiv = document.createElement('div');
        newAddressDiv.className = 'address-entry';
        newAddressDiv.innerHTML = `
            <h4>Address ${addressCount+1}</h4>

             <div>
                <label for="addressType${addressCount}">Address Type:</label>
                <select id="addressType${addressCount}" name="addresses[${addressCount}].addressType">
                  <option value="EMAIL">EMAIL</option>
                  <option value="NUMBER">NUMBER</option>
                  <option value="POSTAL">POSTAL</option>
                </select>
            </div>
            <div>
                <label for="addressValue${addressCount}">Address Value:</label>
                <input type="text" id="addressValue${addressCount}" name="addresses[${addressCount}].addressValue">
            </div>
        `;
        container.appendChild(newAddressDiv);
        addressCount++;
        console.log("New address field added. Total count: " + addressCount);
    } catch (error) {
        console.error("Error adding address fields:", error);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    addressCount = document.querySelectorAll('#addressesContainer .address-entry').length;
    console.log("Initial address count: " + addressCount);
});