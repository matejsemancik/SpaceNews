import SwiftUI
import SpaceNewsKit

struct RootNavigationView: View {
    
    private let rootNavHost: RootNavHost
    
    init(rootNavHost: RootNavHost) {
        self.rootNavHost = rootNavHost
    }
    
    var body: some View {
        Text("Hello there")
    }
}
