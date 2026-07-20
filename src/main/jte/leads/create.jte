@template.layout.main(content = @`
    <div class="max-w-md mx-auto">
        <div class="bg-white rounded-lg shadow-md p-6">
            <h2 class="text-2xl font-bold mb-6">Добавить нового лида</h2>

            <form action="/leads" method="post" class="space-y-4">
                <div>
                    <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                    <input type="email" id="email" name="email" required
                           class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                           placeholder="example@company.com"/>
                </div>

                <div>
                    <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">Телефон</label>
                    <input type="text" id="phone" name="phone" required
                           class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                           placeholder="+7 900 000-00-00"/>
                </div>

                <div>
                    <label for="company" class="block text-sm font-medium text-gray-700 mb-1">Компания</label>
                    <input type="text" id="company" name="company" required
                           class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                           placeholder="Acme"/>
                </div>

                <div>
                    <label for="status" class="block text-sm font-medium text-gray-700 mb-1">Статус</label>
                    <select id="status" name="status" required
                            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500">
                        <option value="NEW">NEW</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                        <option value="CLOSED">CLOSED</option>
                    </select>
                </div>

                <div>
                    <label for="city" class="block text-sm font-medium text-gray-700 mb-1">Город</label>
                    <input type="text" id="city" name="city" required
                           class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                           placeholder="Москва"/>
                </div>

                <div>
                    <label for="street" class="block text-sm font-medium text-gray-700 mb-1">Улица</label>
                    <input type="text" id="street" name="street"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                           placeholder="Тверская, 1"/>
                </div>

                <div>
                    <label for="zip" class="block text-sm font-medium text-gray-700 mb-1">Индекс</label>
                    <input type="text" id="zip" name="zip" required
                           class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500"
                           placeholder="101000"/>
                </div>

                <button type="submit"
                        class="w-full bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-500">
                    Создать лида
                </button>

                <a href="/leads" class="block text-center text-sm text-gray-600 hover:text-gray-900">
                    Отмена
                </a>
            </form>
        </div>
    </div>
`)