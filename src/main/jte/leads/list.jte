@param java.util.List<ru.mentee.power.crm.domain.Lead> leads
@param ru.mentee.power.crm.domain.LeadStatus currentFilter

@template.layout.main(content = @`
    <div class="bg-white rounded-lg shadow-md p-6">
        <div class="flex justify-between items-center mb-4">
            <h2 class="text-2xl font-bold">Lead List</h2>
            <a href="/leads/new"
               class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                + Добавить лида
            </a>
        </div>

        <div class="mb-4 flex gap-2">
            <a href="/leads"
               class="px-4 py-2 rounded ${currentFilter == null ? "bg-blue-500 text-white" : "bg-gray-200"}">
                Все
            </a>
            <a href="/leads?status=NEW"
               class="px-4 py-2 rounded ${currentFilter == ru.mentee.power.crm.domain.LeadStatus.NEW ? "bg-blue-500 text-white" : "bg-gray-200"}">
                NEW
            </a>
            <a href="/leads?status=IN_PROGRESS"
               class="px-4 py-2 rounded ${currentFilter == ru.mentee.power.crm.domain.LeadStatus.IN_PROGRESS ? "bg-blue-500 text-white" : "bg-gray-200"}">
                IN_PROGRESS
            </a>
            <a href="/leads?status=CLOSED"
               class="px-4 py-2 rounded ${currentFilter == ru.mentee.power.crm.domain.LeadStatus.CLOSED ? "bg-blue-500 text-white" : "bg-gray-200"}">
                CLOSED
            </a>
        </div>

        @if(currentFilter != null)
            <p class="text-sm text-gray-600 mb-2">Статус: ${currentFilter}</p>
        @endif

        <table class="min-w-full bg-white border border-gray-200">
            <thead class="bg-gray-100">
            <tr>
                <th class="px-4 py-2 text-left">Email</th>
                <th class="px-4 py-2 text-left">Company</th>
                <th class="px-4 py-2 text-left">Status</th>
            </tr>
            </thead>
            <tbody>
            @for(var lead : leads)
                <tr class="border-t hover:bg-gray-50">
                    <td class="px-4 py-2">${lead.contact().email()}</td>
                    <td class="px-4 py-2">${lead.company()}</td>
                    <td class="px-4 py-2">
                            <span class="px-2 py-1 rounded text-sm bg-green-100 text-green-800">
                                ${lead.status()}
                            </span>
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`)