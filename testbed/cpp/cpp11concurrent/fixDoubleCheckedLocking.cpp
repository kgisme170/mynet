#include <atomic>
#include <mutex>
using namespace std;
struct Singleton {
	static Singleton* getInstance();
};
static mutex m;
static atomic<Singleton*> m_Instance;
Singleton* Singleton::getInstance() {
	auto tmp = m_Instance.load(memory_order_acquire);
	if (tmp == nullptr) {
		lock_guard<mutex> lock(m);
		tmp = m_Instance.load(memory_order_relaxed);
		if (tmp == nullptr) {
			tmp = new Singleton();
			m_Instance.store(tmp, memory_order_release);
		}
	}
	return tmp;
}

int main() {
	auto inst = Singleton::getInstance();
    return 0;
}
