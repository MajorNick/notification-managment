document.addEventListener('DOMContentLoaded', function() {
    createChart('notificationStatuses', notificationStatuses, 'Notification Statuses');
    createChart('notificationPreferences', notificationPreferences, 'Notification Preferences');
});
const colors = [
    'rgba(255, 99, 132, 0.7)',
    'rgba(54, 162, 235, 0.7)',
    'rgba(255, 206, 86, 0.7)',
    'rgba(75, 192, 192, 0.7)',
    'rgba(153, 102, 255, 0.7)'
];
function createChart(canvasId, data, label) {
    new Chart(canvasId, {
        type: "pie",
        data: {
            labels: Object.keys(data),
            datasets: [{
                label: label,
                data: Object.values(data),
                backgroundColor: colors
            }]
        },
        options: {
            responsive: true,
            scales: {y: {beginAtZero: true}}
        }
    });
}