import SwiftUI
import SpaceNewsKit

struct RootNavigationView: View {
    
    private let rootNavHost: RootNavHost
    
    @State private var childStack: ChildStack<RootDestination, RootChild>
    private let actions: RootNavHostActions
    
    init(rootNavHost: RootNavHost) {
        self.rootNavHost = rootNavHost
        self.childStack = rootNavHost.childStack.value
        self.actions = rootNavHost.actions
    }
    
    var body: some View {
        DecomposeNavigationStack(
            kotlinStack: rootNavHost.childStack,
            setPath: { newPath in
                actions.navigate(newStack: newPath)
            }
        ) { child in
            switch onEnum(of: child) {
            case .home(let instance):
                HomeScreenView(instance.screen)
                    .ignoresSafeArea()
            case .articleDetail(let instance):
                Text("ArticleDetail: \(instance.screen)")
                    .navigationTitle("Detail")
            }
        }
    }
}
